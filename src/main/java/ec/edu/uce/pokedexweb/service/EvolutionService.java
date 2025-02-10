package ec.edu.uce.pokedexweb.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class EvolutionService {

    private final WebClient webClient;

    public EvolutionService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Obtiene la cadena evolutiva de un Pokémon por su nombre de especie.
     *
     * @param speciesName Nombre de la especie del Pokémon.
     * @return Mono con una lista de mapas que representan los detalles evolutivos.
     */
    public Mono<List<Map<String, Object>>> getEvolutionChain(String speciesName) {
        return fetchSpeciesData(speciesName)
                .flatMap(this::fetchEvolutionChain)
                .map(this::extractEvolutionDetails)
                .onErrorResume(e -> {
                    System.err.println("Error al obtener la evolución de " + speciesName + ": " + e.getMessage());
                    return Mono.just(Collections.emptyList());
                });
    }

    /**
     * Obtiene los datos de la especie del Pokémon.
     */
    private Mono<Map<String, Object>> fetchSpeciesData(String speciesName) {
        return webClient.get()
                .uri("/pokemon-species/{name}", speciesName)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .onErrorResume(e -> {
                    System.err.println("Error al obtener datos de especie para " + speciesName + ": " + e.getMessage());
                    return Mono.empty();
                });
    }

    /**
     * Obtiene la cadena evolutiva desde la URL proporcionada.
     */
    private Mono<Map<String, Object>> fetchEvolutionChain(Map<String, Object> speciesData) {
        return Optional.ofNullable(speciesData.get("evolution_chain"))
                .map(chain -> (String) ((Map<String, Object>) chain).get("url"))
                .map(url -> webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                        })
                        .onErrorResume(e -> {
                            System.err.println("Error al obtener cadena evolutiva: " + e.getMessage());
                            return Mono.empty();
                        }))
                .orElse(Mono.empty());
    }

    /**
     * Extrae la estructura de evolución en una lista plana.
     */
    private List<Map<String, Object>> extractEvolutionDetails(Map<String, Object> chainData) {
        return chainData != null ? flattenEvolutionChain((Map<String, Object>) chainData.get("chain")) : List.of();
    }

    /**
     * Descompone la estructura de evolución en una lista sencilla.
     */
    private List<Map<String, Object>> flattenEvolutionChain(Map<String, Object> chain) {
        List<Map<String, Object>> evolutions = new ArrayList<>();
        traverseEvolutionChain(chain, evolutions);
        return evolutions;
    }

    /**
     * Recorrido recursivo para extraer la evolución de cada Pokémon.
     */
    private void traverseEvolutionChain(Map<String, Object> chain, List<Map<String, Object>> evolutions) {
        if (chain == null) return;

        evolutions.add(Map.of(
                "species", chain.get("species"),
                "evolution_details", chain.getOrDefault("evolution_details", List.of())
        ));

        ((List<Map<String, Object>>) chain.getOrDefault("evolves_to", List.of()))
                .forEach(nextEvolution -> traverseEvolutionChain(nextEvolution, evolutions));
    }
}
