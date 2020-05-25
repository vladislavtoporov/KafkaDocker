package com.mera.education.grpc.task;

import com.mera.education.grpc.proto.task.Task;
import com.mera.education.grpc.proto.task.TaskRequest;
import com.mera.education.grpc.proto.task.TaskResponse;
import com.mera.education.grpc.proto.task.TaskServiceGrpc;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {
    private Logger logger;
    private Server server;
    @Autowired
    private MessageProducer producer;
    @Value("${grpc.server.port}")
    private Integer gRPCServerPort;

    @PostConstruct
    private void postConstruct() throws IOException, InterruptedException {
        logger = LoggerFactory.getLogger(TaskServiceImpl.class);
        server = ServerBuilder.forPort(gRPCServerPort)
                .addService((BindableService) this)
                .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully stopped the server");
        }));

        server.awaitTermination();
    }

    @Override
    public void task(TaskRequest req, StreamObserver<TaskResponse> responseObserver) {
        Task task = req.getTask();
        logger.info("Incoming Message from gRPC Client " + task.getMessage());
        TaskResponse response = TaskResponse.newBuilder().setResult(task.getMessage()).build();
        producer.sendMessage(task.getMessage());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
