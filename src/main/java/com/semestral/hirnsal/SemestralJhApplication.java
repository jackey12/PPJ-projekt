package com.semestral.hirnsal;

import com.semestral.hirnsal.configuration.JpaConfiguration;
import com.semestral.hirnsal.configuration.MongoConfiguration;
import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.repositories.BasePictureTagRepository;
import com.semestral.hirnsal.db.tables.AutorTable;
import com.semestral.hirnsal.db.tables.CommentTable;
import com.semestral.hirnsal.db.tables.PictureTable;
import com.semestral.hirnsal.db.tables.PictureTagTable;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

		logger.debug("Debbug, main");
		logger.error("Errorr, main");
		logger.info("Info, main");
		logger.trace("Trace, main");
		logger.warn("Warn, main");
	}


	@Bean
	public CommandLineRunner demo(BaseAutorRepository baseAutorRepository, BaseCommentRepository baseCommentRepository, BasePictureRepository basePictureRepository, BasePictureTagRepository basePictureTagRepository) {
		return (args) -> {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String name = "Jackey";
			AutorTable ja = new AutorTable(UUID.randomUUID(), name, date);
			baseAutorRepository.save(ja);
			logger.info("created Autor " + name + ", created at " + dateFormat.format(date));

			name = "Jakub";
			baseAutorRepository.save(new AutorTable(UUID.randomUUID(), name, date));
			logger.info("created Autor " + name + ", created at " + dateFormat.format(date));


			PictureTable pictureTable = new PictureTable(UUID.randomUUID(), "introducing Jericho", "https://i.ytimg.com/vi/S0GOh3nGlik/maxresdefault.jpg", date);
			pictureTable.setAutor(ja);
			List<PictureTagTable> tags = new ArrayList<>();
			tags.add(new PictureTagTable(UUID.randomUUID(), "iron man", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "jericho", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "missile test", pictureTable));
			pictureTable.setTags(tags);
			basePictureRepository.save(pictureTable);
			basePictureTagRepository.save(tags);
			logger.info("Created picture " + pictureTable.getName() + ", URL :  " + pictureTable.getPictureURL());


			pictureTable = new PictureTable(UUID.randomUUID(), "Iron man returns", "https://youngcinemabuffs.files.wordpress.com/2016/01/iron-man-walking-away-from-explosions-wallpaper-53437abd4822d-marvel-needs-a-strong-return-to-gaming-here-s-how-jpeg-3003921.jpg", date);
			pictureTable.setAutor(ja);
			tags = new ArrayList<>();
			tags.add(new PictureTagTable(UUID.randomUUID(), "iron man", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "returns", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "in action", pictureTable));
			pictureTable.setTags(tags);
			basePictureRepository.save(pictureTable);
			basePictureTagRepository.save(tags);
			logger.info("Created picture " + pictureTable.getName() + ", URL :  " + pictureTable.getPictureURL());


			pictureTable = new PictureTable(UUID.randomUUID(), "IM house", "https://i.ytimg.com/vi/dP5vYIvni0A/maxresdefault.jpg", date);
			pictureTable.setAutor(ja);
			tags = new ArrayList<>();
			tags.add(new PictureTagTable(UUID.randomUUID(), "iron man", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "House", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "cliff", pictureTable));
			pictureTable.setTags(tags);
			basePictureRepository.save(pictureTable);
			basePictureTagRepository.save(tags);

			logger.info("Created picture " + pictureTable.getName() + ", URL :  " + pictureTable.getPictureURL());

			CommentTable commentTable = new CommentTable(UUID.randomUUID(), "Celkem solidni obrazek", ja, pictureTable, date);
			baseCommentRepository.save(commentTable);
			logger.info("Created comment: " + commentTable.getCommentText());
			commentTable = new CommentTable(UUID.randomUUID(), "Otřesný", ja, pictureTable, date);
			baseCommentRepository.save(commentTable);
			logger.info("Created comment: " + commentTable.getCommentText());
		};
	}

}
