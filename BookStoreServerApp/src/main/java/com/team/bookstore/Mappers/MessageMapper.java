package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.MessageRequest;
import com.team.bookstore.Dtos.Responses.MessageResponse;
import com.team.bookstore.Entities.Message;
import com.team.bookstore.Entities.User;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "message_status",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "sender",ignore = true)
    @Mapping(target = "receiver",source = "receiver_id",qualifiedByName =
            "toReceiver")
    Message toMessage(MessageRequest messageRequest);
    @Named("toReceiver")
    default User toReceiver(int receiver_id){
        User user = new User();
        user.setId(receiver_id);
        return user;
    }
    @Mapping(target = "sender_id",source = "sender",qualifiedByName =
            "toSender_id")
    @Mapping(target = "receiver_id",source = "receiver",qualifiedByName =
            "toReceiver_id")
    MessageResponse toMessageResponse(Message message);
    @Named("toSender_id")
    default int toSender_id(User sender){
        return sender.getId();
    }
    @Named("toReceiver_id")
    default int toReceiver_id(User receiver){
        return receiver.getId();
    }
}
