package producer;

import datagenerator.InvoiceGenerator;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import types.PosInvoice;

public class InvoiceDispatcher implements Runnable{


    private String topicName;
    private KafkaProducer<Integer, PosInvoice> kafkaProducer;
    private int produceSpeed;
    private InvoiceGenerator invoiceGenerator=InvoiceGenerator.getInstance();


    InvoiceDispatcher(String topicName,KafkaProducer<Integer, PosInvoice> kafkaProducer,int produceSpeed)
    {
        this.topicName=topicName;
        this.kafkaProducer=kafkaProducer;
        this.produceSpeed=produceSpeed;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(produceSpeed);
            kafkaProducer.send(new ProducerRecord<>(topicName,null,invoiceGenerator.getNextInvoice()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
