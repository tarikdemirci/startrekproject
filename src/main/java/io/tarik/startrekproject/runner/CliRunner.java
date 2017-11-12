package io.tarik.startrekproject.runner;

import io.tarik.startrekproject.stapi.service.StapiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CliRunner implements CommandLineRunner{

    private final StapiService stapiService;

    @Autowired
    public CliRunner(StapiService stapiService) {
        this.stapiService = stapiService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            stapiService.getSpeciesOfName(args[0])
                    .ifPresent(species -> System.out.println("Hello World: " + species));
        } else {
            System.out.println("Please specify <name>");
        }

    }
}
