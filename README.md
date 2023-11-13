#Create Data Base in postgresql or sql server( for this database modify the script db_user.sql), 
the script sql is in the project

#Dowload the project o clone

    git clone -b main https://github.com/rverat/business-user-management-v1.git

#requirements

   - java 21
   - IDE of your preference

  for arch linux you would use it
    
   - yay -S intellij-idea-community
   - yay -S jdk-temurin Temurin (OpenJDK 21 Java binaries by Adoptium, formerly AdoptOpenJDK)

#Compile program
    
    mvn clean install
  
  when you compile the program, you can run

#Postman request and spring documentation

    https://documenter.getpostman.com/view/21762368/2s9YXh5hzD


#Create docker image
  you need install docker and docker buildx and start    
  open terminal in directory docker and execute this comands

    //create builder example:business-users-management-builder, and use that builder
    sudo docker buildx create --name business-users-management-builder
    sudo docker buildx create --use business-users-management-builder

    //create image example business-users-management-v1
    sudo docker buildx build --builder business-users-management-builder -t rverat/business-users-management-v1 .

    //load the build
    sudo docker buildx build --load -t business-users-management-v1 .

    //run
    sudo docker run -p 9082:9082 business-users-management-v1

    // see logs
    sudo docker logs <id_run_container>

    //Stop Container and delete

    sudo docker stop <container_id>
    sudo docker rm <container_id>

    //push your image to docker hub add username of docker hub rverat and tag 0.0.1
    sudo docker login
    sudo docker tag business-users-management-v1 rverat/business-users-management-v1:0.0.1
    sudo docker push rverat/business-users-management-v1:0.0.1


#For you use your database local you need configure this files pg_hba.conf and postgresql.conf
![](/home/dev/java/business-users-management-v1/images/pg_hba.conf.png)
![](/home/dev/java/business-users-management-v1/images/postgresql.conf.png)

and restart your postgresql service!
   
