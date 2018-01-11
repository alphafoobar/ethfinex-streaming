import info.bitrich.xchangestream.bitfinex.BitfinexStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bitfinex {

    private static final Logger loggerBitfinex = LoggerFactory.getLogger("BIT");
    private static final Logger loggerEthfinex = LoggerFactory.getLogger("eth");

    public static void main(String... args) {
        StreamingExchange exchangeEth = StreamingExchangeFactory.INSTANCE
            .createExchange(EthfinexStreamingExchange.class.getName());
        StreamingExchange exchangeBit = StreamingExchangeFactory.INSTANCE
            .createExchange(BitfinexStreamingExchange.class.getName());

// Connect to the Exchange WebSocket API. Blocking wait for the connection.
        exchangeEth.connect().blockingAwait();
        exchangeBit.connect().blockingAwait();

// Subscribe to live trades update.
//        exchange.getStreamingMarketDataService()
//            .getTrades(CurrencyPair.BTC_USD)
//            .subscribe(trade -> {
//                logger.info("Incoming trade: {}", trade);
//            }, throwable -> {
//                logger.error("Error in subscribing trades.", throwable);
//            });

// Subscribe order book data with the reference to the subscription.
//        Disposable subscription = exchange.getStreamingMarketDataService()
//            .getOrderBook(CurrencyPair.BTC_USD)
//            .subscribe(orderBook -> {
//                // Do something
//            });

        exchangeEth.getStreamingMarketDataService()
            .getTicker(CurrencyPair.ETH_USD)
            .subscribe(ticker -> {
                loggerEthfinex.info("Incoming ticker: {}", ticker);
            }, throwable -> {
                loggerEthfinex.error("Error in subscribing ticker.", throwable);
            });
        exchangeBit.getStreamingMarketDataService()
            .getTicker(CurrencyPair.ETH_USD)
            .subscribe(ticker -> {
                loggerBitfinex.info("Incoming ticker: {}", ticker);
            }, throwable -> {
                loggerBitfinex.error("Error in subscribing ticker.", throwable);
            });
// Unsubscribe from data order book.
//        subscription.dispose();

// Disconnect from exchange (non-blocking)
        exchangeEth.disconnect().subscribe(() -> loggerEthfinex.info("Disconnected from the Exchange"));
        exchangeBit.disconnect().subscribe(() -> loggerBitfinex.info("Disconnected from the Exchange"));
    }

}
