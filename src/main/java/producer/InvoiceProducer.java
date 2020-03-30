package producer;


import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import configs.AppConfigs;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serde.JsonSerializer;
import types.PosInvoice;

public class InvoiceProducer {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String [] args)
    {
       /* String topicname=args[0];
        int producerThreads=Integer.valueOf(args[1]);
        int produceSpeed=Integer.valueOf(args[2]);*/

        String topicname="pos";
        int producerThreads=2;
        int produceSpeed=1000;


        KafkaProducer<Integer,PosInvoice> kafkaProducer=null;

        try
        {
            Properties producerProps = new Properties();
            producerProps.put(ProducerConfig.CLIENT_ID_CONFIG,AppConfigs.applicationID);
            producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,AppConfigs.bootstrapServers);
            producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
            producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

            kafkaProducer = new KafkaProducer<Integer,PosInvoice>(producerProps);

            Thread [] invoiceDispatchers=new Thread[producerThreads];

            for (int i=0;i<invoiceDispatchers.length;i++)
            {
                invoiceDispatchers[i]=new Thread(new InvoiceDispatcher(topicname,kafkaProducer,produceSpeed));
                invoiceDispatchers[i].start();
            }


            for (Thread t :invoiceDispatchers)
            {
                t.join();
            }

        }
        catch (InterruptedException exception)
        {
            logger.error("Interuptted exception.");
        }
        finally
        {
            kafkaProducer.close();
        }
    }


}
