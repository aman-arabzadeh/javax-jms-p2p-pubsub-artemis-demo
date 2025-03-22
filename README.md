# JMS Java Examples – Queue (P2P) & Topic (Pub/Sub)

Detta projekt visar hur man använder **Java Message Service (JMS)** med `javax.jms` och **Apache ActiveMQ Artemis**.

## Innehåll

- **P2P (Queue):** Point-To-Point kommunikation med kö (One To One)
- **Pub/Sub (Topic):** Publicera till flera konsumenter (One To Many)
---
## Ladda ner: 
[Ladda ner Apache ActiveMQ Artemis](https://activemq.apache.org/components/artemis/download/)


## Starta Artemis Broker (lokalt) På Windows använd:

1. Gå till Artemis `bin`-mapp Skapa en broker-instans:
.\artemis.cmd create ..\myBroker

3. Gå till gå till skapade broker instans sök väg `bin`-mapp Starta brokern:
cd ..\myBroker\bin
.\artemis.cmd run


