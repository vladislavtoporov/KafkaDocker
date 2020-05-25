package com.mera.education.grpc.task;

import com.mera.education.grpc.proto.task.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GrpcClientService {
    private Logger logger;
    private TaskServiceGrpc.TaskServiceBlockingStub syncClient;
    @Value("${grpc.server.address}")
    private String gRPCServerAddress;
    @Value("${grpc.server.port}")
    private Integer gRPCServerPort;
    @PostConstruct
    public void postConstruct() {
        logger = LoggerFactory.getLogger(GrpcClientService.class);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(gRPCServerAddress, gRPCServerPort)
                .usePlaintext()
                .build();
        syncClient = TaskServiceGrpc.newBlockingStub(channel);
    }

    public String sendMessage(final String message) {
        logger.info("Sent message to server via gRPC");
        try {
            Task tasking = Task.newBuilder()
                    .setMessage(message)
                    .build();

            final TaskResponse response = this.syncClient.task(TaskRequest.newBuilder().setTask(tasking).build());
            return response.getResult();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }

    }
}
