package com.semestral.hirnsal;

import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.tables.AutorTable;
import com.semestral.hirnsal.db.tables.CommentTable;
import com.semestral.hirnsal.db.tables.PictureTable;
import com.semestral.hirnsal.db.tables.PictureTagTable;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SemestralJhApplication.class)
@WebAppConfiguration
@ActiveProfiles({"test"})
public class SemestralJhApplicationTests {

	@Autowired
	BasePictureRepository basePictureRepository;
	@Autowired
	BaseCommentRepository baseCommentRepository;
	@Autowired
	BaseAutorRepository baseAutorRepository;

	private static final String autorNameToFind = "Jackey(unikatninazevprotest)";
	private static final UUID autorIDToFind = UUID.randomUUID();
	private static final UUID autorID2ToFind = UUID.randomUUID();
	private static final String autorNameToFind2 = "Jakub Hirnšal(unikatninazevprotest)";
	private static final UUID autorIDToFind2 = UUID.randomUUID();

	private static final String pictureToFind = "introducing Jericho";
	private static final String pictureToFind2 = "Jakub Hirnšal";
	private static final String TagToFind = "iron man";

	@Before
	public void beforeTests() {
		Date date = new Date();
		baseAutorRepository.save(new AutorTable(autorIDToFind, autorNameToFind, date));
		baseAutorRepository.save(new AutorTable(autorID2ToFind, autorNameToFind, date));
		AutorTable ja = new AutorTable(autorIDToFind2, autorNameToFind2, date);
		baseAutorRepository.save(ja);

		PictureTable pictureTable = new PictureTable(UUID.randomUUID(), pictureToFind, "http://vignette4.wikia.nocookie.net/ironman/images/9/9d/Tumblr_l1iotoYo541qbn8c7.jpg/revision/latest?cb=20131120195052", date);
		pictureTable.setAutor(ja);
		List<PictureTagTable> tags = new ArrayList<>();
		tags.add(new PictureTagTable(UUID.randomUUID(), TagToFind, pictureTable));
		tags.add(new PictureTagTable(UUID.randomUUID(), "jericho", pictureTable));
		tags.add(new PictureTagTable(UUID.randomUUID(), "missile test", pictureTable));
		pictureTable.setTags(tags);
		basePictureRepository.save(pictureTable);

		pictureTable = new PictureTable(UUID.randomUUID(), "Iron man returns", "http://www.kultura.cz/data/files/iron-man-novinka.jpeg", date);
		pictureTable.setAutor(ja);
		tags = new ArrayList<>();
		tags.add(new PictureTagTable(UUID.randomUUID(), TagToFind, pictureTable));
		tags.add(new PictureTagTable(UUID.randomUUID(), "returns", pictureTable));
		tags.add(new PictureTagTable(UUID.randomUUID(), "in action", pictureTable));
		pictureTable.setTags(tags);
		basePictureRepository.save(pictureTable);

		CommentTable commentTable = new CommentTable(UUID.randomUUID(), "Celkem solidni obrazek", ja, pictureTable, date);
		baseCommentRepository.save(commentTable);
		commentTable = new CommentTable(UUID.randomUUID(), "Otřesný", ja, pictureTable, date);
		baseCommentRepository.save(commentTable);
	}


	@Test
	public void vyhledaniVsehoPodleJmena(){
		List<AutorTable> autors = baseAutorRepository.findByName(autorNameToFind);
		for (AutorTable autor:autors) {
			Assert.assertEquals(autorNameToFind, autor.getName());
		}
		Assert.assertEquals(2, autors.size());

		List<AutorTable> autors2 = baseAutorRepository.findByName(autorNameToFind2);
		for (AutorTable autor:autors2) {
			Assert.assertEquals(autorNameToFind2, autor.getName());
		}
		Assert.assertEquals(1, autors2.size());

		List<PictureTable> pictures = basePictureRepository.findByName(pictureToFind);
		for (PictureTable picture:pictures) {
			Assert.assertEquals(pictureToFind, picture.getName());
		}
		Assert.assertEquals(1, pictures.size());
	}

	@Test
	public void vyhledaniObrazkuPodleAutora(){
		List<PictureTable> pictures  = basePictureRepository.findByAutorId(autorIDToFind2);
		for (PictureTable picture:pictures) {
			Assert.assertEquals(autorIDToFind2, picture.getAutor().getId());
		}
		Assert.assertEquals(2, pictures.size());

		List<PictureTable> pictures2  = basePictureRepository.findByAutorId(autorIDToFind);
		for (PictureTable picture:pictures) {
			//neměl by projít
			Assert.assertEquals(autorIDToFind, picture.getAutor().getId());
		}
		Assert.assertEquals(0, pictures.size());
	}

	@Test
	public void vyhledaniObrazkuPodleTagu(){
		List<PictureTable> pictures = basePictureRepository.findByTags(TagToFind);
		for (PictureTable picture:pictures) {
			List<PictureTagTable> tags = picture.getTags();
				Assert.assertTrue(tags.contains(TagToFind));
		}
		Assert.assertEquals(2, pictures.size());
	}



	@After
	public void after() {
		baseAutorRepository.deleteAll();
		basePictureRepository.deleteAll();
		baseCommentRepository.deleteAll();
	}

}
