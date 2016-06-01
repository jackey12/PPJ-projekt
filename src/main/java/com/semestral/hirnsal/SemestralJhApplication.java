package com.semestral.hirnsal;

import com.semestral.hirnsal.configuration.JpaConfiguration;
import com.semestral.hirnsal.configuration.MongoConfiguration;
import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.repositories.BasePictureTagRepository;
import com.semestral.hirnsal.db.tables.AutorEntity;
import com.semestral.hirnsal.db.tables.CommentEntity;
import com.semestral.hirnsal.db.tables.PictureEntity;
import com.semestral.hirnsal.db.tables.PictureTagEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@SpringBootApplication(exclude = {
		MongoAutoConfiguration.class,
		MongoDataAutoConfiguration.class,
		MongoRepositoriesAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
		DataSourceAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class})
@Import({JpaConfiguration.class, MongoConfiguration.class})
public class SemestralJhApplication {

	private static final Logger logger = LoggerFactory.getLogger(SemestralJhApplication.class);

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}


	public static void main(String[] args) {
		SpringApplication.run(SemestralJhApplication.class, args);
		logger.trace("Application started at "+new Date());
	}
}
