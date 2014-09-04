package br.jus.trt3.poc.jee6.producer;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.logging.Logger;

/**
 * Produz instâncias de loggers para serem usados via @Inject
 * @author denisf
 */
public class LogProducer {
    
    @Produces
    public Logger produz(InjectionPoint pontoInjecao) {
        return Logger.getLogger(pontoInjecao.getMember().getDeclaringClass().getName());
    }
}
