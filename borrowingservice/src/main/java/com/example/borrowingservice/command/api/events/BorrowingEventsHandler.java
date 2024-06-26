package com.example.borrowingservice.command.api.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.borrowingservice.command.api.data.BorrowRepository;
import com.example.borrowingservice.command.api.data.Borrowing;
//import com.example.borrowingservice.command.api.model.Message;
//import com.example.borrowingservice.command.api.service.BorrowService;
//import com.example.borrowingservice.command.api.service.IBorrowService;

@Component
public class BorrowingEventsHandler {

	@Autowired
	private BorrowRepository borrowRepository;
	
//	@Autowired
//	private IBorrowService borrowService;
	
	@EventHandler
	public void on(BorrowCreatedEvent event) {
		Borrowing model = new Borrowing();
		
		BeanUtils.copyProperties(event, model);
		
		borrowRepository.save(model);
	}
	@EventHandler
	public void on(BorrowDeletedEvent event) {
		if(borrowRepository.findById(event.getId()).isPresent()) {
			borrowRepository.deleteById(event.getId());
		}
		else return;
		
	}
	@EventHandler
	public void on(BorrowingUpdateBookReturnEvent event) {
		Borrowing model = borrowRepository.findByEmployeeIdAndBookIdAndReturnDateIsNull(event.getEmployee(), event.getBookId());
		model.setReturnDate(event.getReturnDate());
		borrowRepository.save(model);
	}
//	@EventHandler
//	public void on(BorrowSendMessageEvent event) {
//		Message message = new Message(event.getEmployeeId(), event.getMessage());
//		borrowService.sendMessage(message);
//	}
}