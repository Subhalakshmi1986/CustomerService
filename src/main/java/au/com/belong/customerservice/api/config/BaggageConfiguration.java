package au.com.belong.customerservice.api.config;

import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaggageConfiguration {

    @Bean
    BaggageField customerField() {
        return BaggageField.create("customer-id");
    }

    @Bean
    BaggageField phonenumberField() {
        return BaggageField.create("phonenumber-id");
    }

    @Bean
    CurrentTraceContext.ScopeDecorator mdcScopeDecorator() {
        return MDCScopeDecorator.newBuilder()
                .clear()
                .add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(customerField())
                        .flushOnUpdate()
                        .build())
                .add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(phonenumberField())
                        .flushOnUpdate()
                        .build())
                .build();
    }
}
