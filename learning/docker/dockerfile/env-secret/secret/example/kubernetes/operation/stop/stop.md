# Stop a Pod Without Deleting the Deployment

1. discover the deployment name:

     ```commandline
     kubectl get deployments
     ```

   output:

     ```commandline
     NAME       READY   UP-TO-DATE   AVAILABLE   AGE
     java-app   1/1     1            1           17m
     ```
2. set the replicas to zero:

     ```commandline
     kubectl scale deployment java-app --replicas=0
     ```

3. bring the replicas back to 1:

     ```commandline
     kubectl scale deployment java-app --replicas=1
     ```
