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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
			baseAutorRepository.save(new AutorTable(name, date));
			logger.info("created Autor " + name + ", created at " + dateFormat.format(date));


			PictureTable pictureTable = new PictureTable("introducing Jericho", "http://vignette4.wikia.nocookie.net/ironman/images/9/9d/Tumblr_l1iotoYo541qbn8c7.jpg/revision/latest?cb=20131120195052");
			List<PictureTagTable> tags = new ArrayList<>();
			tags.add(new PictureTagTable("iron man", pictureTable));
			tags.add(new PictureTagTable("jericho", pictureTable));
			tags.add(new PictureTagTable("missile test", pictureTable));
			pictureTable.setTags(tags);
			basePictureRepository.save(pictureTable);
			logger.info("Created picture " + pictureTable.getName() + ", URL :  " + pictureTable.getPictureURL());

		};
	}

}
