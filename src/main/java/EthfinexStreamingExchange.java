import info.bitrich.xchangestream.bitfinex.BitfinexStreamingMarketDataService;
import info.bitrich.xchangestream.bitfinex.BitfinexStreamingService;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;

public class EthfinexStreamingExchange extends BitfinexExchange implements StreamingExchange {
    private static final String API_URI = "wss://api.ethfinex.com/ws/2";

    private final BitfinexStreamingService streamingService;
    private BitfinexStreamingMarketDataService streamingMarketDataService;

    public EthfinexStreamingExchange() {
        this.streamingService = new BitfinexStreamingService(API_URI);
    }

    @Override
    protected void initServices() {
        super.initServices();
        streamingMarketDataService = new BitfinexStreamingMarketDataService(streamingService);
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        return streamingService.connect();
    }

    @Override
    public Completable disconnect() {
        return streamingService.disconnect();
    }

    @Override
    public boolean isAlive() {
        return streamingService.isSocketOpen();
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = super.getDefaultExchangeSpecification();
        spec.setShouldLoadRemoteMetaData(false);

        return spec;
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }
}
