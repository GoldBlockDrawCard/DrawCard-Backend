package draw.card.drawcard.config;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IPFSConfiguration {

    @Value("${ipfs.address}")
    private String ipfsAddress;

    @Bean
    public IPFS ipfs() {
        return new IPFS(ipfsAddress);
    }
}