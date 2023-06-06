package in.mypractice.food.ordering.system.payment.helper;

import in.mypractice.food.ordering.system.payment.mapper.PaymentDataMapper;
import in.mypractice.food.ordering.system.payment.port.output.repository.CreditEntryHistoryRepository;
import in.mypractice.food.ordering.system.payment.port.output.repository.CreditEntryRepository;
import in.mypractice.food.ordering.system.payment.port.output.repository.PaymentRepository;
import in.mypractice.food.ordering.system.payment.service.domain.service.PaymentDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRequestHelper {
    public  final PaymentDomainService paymentDomainService;
    public  final PaymentDataMapper paymentDataMapper;
    public  final PaymentRepository paymentRepository;
    public  final CreditEntryRepository creditEntryRepository;
    public  final CreditEntryHistoryRepository creditEntryHistoryRepository;

}
