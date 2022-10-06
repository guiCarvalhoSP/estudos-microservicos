package com.estudos.bookservice.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// Exemplo de erro para testar o resilience4j
@Tag(name = "Foo Bar")
@RestController
@RequestMapping("book-service")
public class FooBarController {

    private final Logger logger = LoggerFactory.getLogger((FooBarController.class));

    @Operation(summary = "Foo-bar")
    @GetMapping("/foo-bar")
    //Vai tentar fazer a conexão um determinado número de vezes, se não retorna o fallback. Configura no yml ou properties
    //@Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")

    //Quando ocorre a falha, ele abre o método e já vai para o fallback direto para as próximas requisições, depois de um tempo ele volta para
    //estado fechado e se ocorrer uma alta taxa de falha, novamente abre o circuito e não permite o uso do método, indo ao fallback direto.
    //@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")

    // Define o número limite que um endpoint vai receber de requisições durante um período de tempo
    //@RateLimiter(name = "default")

    //Serve para limitar o limite máximo de chamadas concorrentes ao mesmo endpoint.
    //@Bulkhead(name = "default")
    public String fooBar() {
        logger.info("Request to  Foo-bar is received");
        return  "foo-bar";

        //Descomentar o código para testar o retry ou circuit breaker.
        /*var response = new RestTemplate()
         *       .getForEntity("http://localhost:8080/foo-bar", String.class);
        return response.getBody();*/
    }
}
