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


	@Bean
	public CommandLineRunner demo(BaseAutorRepository baseAutorRepository, BaseCommentRepository baseCommentRepository, BasePictureRepository basePictureRepository, BasePictureTagRepository basePictureTagRepository) {
		return (args) -> {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String name = "Jackey";
			AutorEntity ja = new AutorEntity(UUID.randomUUID(), name, date);
			baseAutorRepository.save(ja);
			logger.debug("created Autor " + name + ", created at " + dateFormat.format(date));

			name = "Jakub";
			baseAutorRepository.save(new AutorEntity(UUID.randomUUID(), name, date));
			logger.debug("created Autor " + name + ", created at " + dateFormat.format(date));


			PictureEntity pictureEntity = new PictureEntity(UUID.randomUUID(), "introducing Jericho", "https://i.ytimg.com/vi/S0GOh3nGlik/maxresdefault.jpg", date);
			pictureEntity.setAutor(ja);
			List<PictureTagEntity> tags = new ArrayList<>();
			tags.add(new PictureTagEntity(UUID.randomUUID(), "iron man", pictureEntity));
			tags.add(new PictureTagEntity(UUID.randomUUID(), "jericho", pictureEntity));
			tags.add(new PictureTagEntity(UUID.randomUUID(), "missile test", pictureEntity));
			pictureEntity.setTags(tags);
			basePictureRepository.save(pictureEntity);
			basePictureTagRepository.save(tags);
			logger.debug("Created picture " + pictureEntity.getName() + ", URL :  " + pictureEntity.getPictureURL());


			pictureEntity = new PictureEntity(UUID.randomUUID(), "Iron man returns", "https://youngcinemabuffs.files.wordpress.com/2016/01/iron-man-walking-away-from-explosions-wallpaper-53437abd4822d-marvel-needs-a-strong-return-to-gaming-here-s-how-jpeg-3003921.jpg", date);
			pictureEntity.setAutor(ja);
			tags = new ArrayList<>();
			tags.add(new PictureTagEntity(UUID.randomUUID(), "iron man", pictureEntity));
			tags.add(new PictureTagEntity(UUID.randomUUID(), "returns", pictureEntity));
			tags.add(new PictureTagEntity(UUID.randomUUID(), "in action", pictureEntity));
			pictureEntity.setTags(tags);
			basePictureRepository.save(pictureEntity);
			basePictureTagRepository.save(tags);
			logger.debug("Created picture " + pictureEntity.getName() + ", URL :  " + pictureEntity.getPictureURL());


			pictureEntity = new PictureEntity(UUID.randomUUID(), "IM house", "https://i.ytimg.com/vi/dP5vYIvni0A/maxresdefault.jpg", date);
			pictureEntity.setAutor(ja);
			tags = new ArrayList<>();
			tags.add(new PictureTagEntity(UUID.randomUUID(), "iron man", pictureEntity));
			tags.add(new PictureTagEntity(UUID.randomUUID(), "House", pictureEntity));
			tags.add(new PictureTagEntity(UUID.randomUUID(), "cliff", pictureEntity));
			pictureEntity.setTags(tags);
			basePictureRepository.save(pictureEntity);
			basePictureTagRepository.save(tags);

			logger.debug("Created picture " + pictureEntity.getName() + ", URL :  " + pictureEntity.getPictureURL());

			CommentEntity commentEntity = new CommentEntity(UUID.randomUUID(), "Celkem solidni obrazek", ja, pictureEntity, date);
			baseCommentRepository.save(commentEntity);
			logger.debug("Created comment: " + commentEntity.getCommentText());
			commentEntity = new CommentEntity(UUID.randomUUID(), "Otřesný", ja, pictureEntity, date);
			baseCommentRepository.save(commentEntity);
			logger.debug("Created comment: " + commentEntity.getCommentText());
		};
	}
}
