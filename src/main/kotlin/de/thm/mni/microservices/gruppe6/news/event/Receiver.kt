package de.thm.mni.microservices.gruppe6.news.event

import de.thm.mni.microservices.gruppe6.lib.event.DataEvent
import de.thm.mni.microservices.gruppe6.lib.event.DomainEvent
import de.thm.mni.microservices.gruppe6.lib.event.EventTopic
import de.thm.mni.microservices.gruppe6.news.service.NewsStorageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.annotation.JmsListeners
import org.springframework.stereotype.Component
import reactor.kotlin.core.publisher.toMono
import javax.jms.Message
import javax.jms.ObjectMessage

/**
 * Class used to receive any Events regarding the microservices workspace
 * @param newsStorageService A service to transfer the events into the project specific context
 */
@Component
class Receiver(private val newsStorageService: NewsStorageService) {

    /**
     * Logger to track errors within receiving message and debugging not implemented message types
     */
    val logger: Logger = LoggerFactory.getLogger(this::class.java)


    /**
     * Listen to all topics specified via the JMSListener destinations, distributes the messages to the corresponding services
     * The ContainerFactory is required to specify the receiving context.
     * @param message a Object message, containing either a DataEvent or DomainEvent within its `object`-payload.
     */
    @JmsListeners(
        JmsListener(destination = EventTopic.DataEvents.topic, containerFactory = "jmsDataEventListenerContainerFactory"),
        JmsListener(destination = EventTopic.DomainEvents_IssueService.topic, containerFactory = "jmsIssueDomainEventListenerContainerFactory"),
        JmsListener(destination = EventTopic.DomainEvents_ProjectService.topic, containerFactory = "jmsProjectDomainEventListenerContainerFactory"),
        JmsListener(destination = EventTopic.DomainEvents_UserService.topic, containerFactory = "jmsUserDomainEventListenerContainerFactory"),
        //No news service
    )
    fun receive(message: Message) {
        try {
            if (message !is ObjectMessage) {
                logger.error("Received unknown message type {} with id {}", message.jmsType, message.jmsMessageID)
                return
            }
            when (val payload = message.`object`) {
                is DataEvent -> {
                    logger.debug("Received DataEvent ObjectMessage with code {} and id {}", payload.code, payload.id)
                    newsStorageService.storeDataEvent(payload.toMono())
                }
                is DomainEvent -> {
                    logger.debug("Received DomainEvent Object Message with code {}", payload.code)
                    newsStorageService.storeDomainEvent(payload.toMono())
                }
                else -> {
                    logger.error(
                        "Received unknown ObjectMessage with payload type {} with id {}",
                        payload.javaClass,
                        message.jmsMessageID
                    )
                }
            }
        } catch (e: Exception) {
            logger.error("Receiver-Error", e)
        }
    }
}
