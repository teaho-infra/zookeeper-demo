package net.teaho.loadbalance.gateway;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-10
 */
public class GatewayStarter {


    private List<Client> clients = new ArrayList<>();

    public static void main(String[] args) {

        GatewayStarter g = new GatewayStarter();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            for (int i=1; i <= 10; i++) {

                executorService.submit(() -> {

                    Client client = new Client();
                    g.clients.add(client);
                    client.connect();

                });
            }

            System.out.println("敲回车键退出！\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            g.clients.forEach(client -> client.disconnect());
            executorService.shutdown();
            executorService.shutdownNow();
        }

    }

}
