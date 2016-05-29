package com.semestral.hirnsal;

import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.tables.AutorTable;
import com.semestral.hirnsal.db.tables.PictureTable;
import com.semestral.hirnsal.db.tables.PictureTagTable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SemestralJhApplication {

	private static final Logger logger = LoggerFactory.getLogger(SemestralJhApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SemestralJhApplication.class, args);

		logger.debug("Debbug, main");
		logger.error("Errorr, main");
		logger.info("Info, main");
		logger.trace("Trace, main");
		logger.warn("Warn, main");
	}

	@Bean
	public CommandLineRunner demo(BaseAutorRepository baseAutorRepository, BaseCommentRepository baseCommentRepository, BasePictureRepository basePictureRepository) {
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


			PictureTable pictureTable = new PictureTable(UUID.randomUUID(), "introducing Jericho", "http://vignette4.wikia.nocookie.net/ironman/images/9/9d/Tumblr_l1iotoYo541qbn8c7.jpg/revision/latest?cb=20131120195052", date);
			pictureTable.setAutor(ja);
			List<PictureTagTable> tags = new ArrayList<>();
			tags.add(new PictureTagTable(UUID.randomUUID(), "iron man", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "jericho", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "missile test", pictureTable));
			pictureTable.setTags(tags);
			basePictureRepository.save(pictureTable);
			logger.info("Created picture " + pictureTable.getName() + ", URL :  " + pictureTable.getPictureURL());


			pictureTable = new PictureTable(UUID.randomUUID(), "Iron man returns", "http://www.kultura.cz/data/files/iron-man-novinka.jpeg", date);
			pictureTable.setAutor(ja);
			tags = new ArrayList<>();
			tags.add(new PictureTagTable(UUID.randomUUID(), "iron man", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "returns", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "in action", pictureTable));
			pictureTable.setTags(tags);
			basePictureRepository.save(pictureTable);
			logger.info("Created picture " + pictureTable.getName() + ", URL :  " + pictureTable.getPictureURL());


			pictureTable = new PictureTable(UUID.randomUUID(), "IM house", "http://vignette2.wikia.nocookie.net/marvelmovies/images/7/7c/TonyStarkMantion-IM3.png/revision/latest?cb=20131127033251", date);
			pictureTable.setAutor(ja);
			tags = new ArrayList<>();
			tags.add(new PictureTagTable(UUID.randomUUID(), "iron man", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "House", pictureTable));
			tags.add(new PictureTagTable(UUID.randomUUID(), "cliff", pictureTable));
			pictureTable.setTags(tags);
			basePictureRepository.save(pictureTable);
			logger.info("Created picture " + pictureTable.getName() + ", URL :  " + pictureTable.getPictureURL());

		};
	}

}
