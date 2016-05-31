package com.semestral.hirnsal;

import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.tables.AutorEntity;
import com.semestral.hirnsal.db.tables.CommentEntity;
import com.semestral.hirnsal.db.tables.PictureEntity;
import com.semestral.hirnsal.db.tables.PictureTagEntity;
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

	private static final String pictureToFind = "introducing Jericho(unikatninazevprotest)";
	private static final String TagToFind = "iron man(unikatninazevprotest)";

	@Before
	public void beforeTests() {
		Date date = new Date();
		baseAutorRepository.save(new AutorEntity(autorIDToFind, autorNameToFind, date));
		baseAutorRepository.save(new AutorEntity(autorID2ToFind, autorNameToFind, date));
		AutorEntity ja = new AutorEntity(autorIDToFind2, autorNameToFind2, date);
		baseAutorRepository.save(ja);

		PictureEntity pictureEntity = new PictureEntity(UUID.randomUUID(), pictureToFind, "http://vignette4.wikia.nocookie.net/ironman/images/9/9d/Tumblr_l1iotoYo541qbn8c7.jpg/revision/latest?cb=20131120195052", date);
		pictureEntity.setAutor(ja);
		List<PictureTagEntity> tags = new ArrayList<>();
		tags.add(new PictureTagEntity(UUID.randomUUID(), TagToFind, pictureEntity));
		tags.add(new PictureTagEntity(UUID.randomUUID(), "jericho", pictureEntity));
		tags.add(new PictureTagEntity(UUID.randomUUID(), "missile test", pictureEntity));
		pictureEntity.setTags(tags);
		basePictureRepository.save(pictureEntity);

		pictureEntity = new PictureEntity(UUID.randomUUID(), "Iron man returns", "http://www.kultura.cz/data/files/iron-man-novinka.jpeg", date);
		pictureEntity.setAutor(ja);
		tags = new ArrayList<>();
		tags.add(new PictureTagEntity(UUID.randomUUID(), TagToFind, pictureEntity));
		tags.add(new PictureTagEntity(UUID.randomUUID(), "returns", pictureEntity));
		tags.add(new PictureTagEntity(UUID.randomUUID(), "in action", pictureEntity));
		pictureEntity.setTags(tags);
		basePictureRepository.save(pictureEntity);

		CommentEntity commentEntity = new CommentEntity(UUID.randomUUID(), "Celkem solidni obrazek", ja, pictureEntity, date);
		baseCommentRepository.save(commentEntity);
		commentEntity = new CommentEntity(UUID.randomUUID(), "Otřesný", ja, pictureEntity, date);
		baseCommentRepository.save(commentEntity);

	}


	@Test
	public void vyhledaniObrazkuPodleJmena(){
		List<PictureEntity> pictures = basePictureRepository.findByName(pictureToFind);
		for (PictureEntity picture:pictures) {
			Assert.assertEquals(pictureToFind, picture.getName());
		}
		Assert.assertEquals(1, pictures.size());
	}

	@Test
	public void vyhledaniAutoraPodleJmena(){
		List<AutorEntity> autors = baseAutorRepository.findByName(autorNameToFind);
		for (AutorEntity autor:autors) {
			Assert.assertEquals(autorNameToFind, autor.getName());
		}
		Assert.assertEquals(2, autors.size());

		List<AutorEntity> autors2 = baseAutorRepository.findByName(autorNameToFind2);
		for (AutorEntity autor:autors2) {
			Assert.assertEquals(autorNameToFind2, autor.getName());
		}
		Assert.assertEquals(1, autors2.size());

	}

	@Test
	public void vyhledaniObrazkuPodleAutora(){
		List<PictureEntity> pictures  = basePictureRepository.findByAutorId(autorIDToFind2);
		for (PictureEntity picture:pictures) {
			Assert.assertEquals(autorIDToFind2, picture.getAutor().getId());
		}
		Assert.assertEquals(2, pictures.size());

		List<PictureEntity> pictures2  = basePictureRepository.findByAutorId(autorIDToFind);
		for (PictureEntity picture:pictures2) {
			//neměl by projít
			Assert.assertEquals(autorIDToFind, picture.getAutor().getId());
		}
		Assert.assertEquals(0, pictures2.size());
	}

	@Test
	public void vyhledaniObrazkuPodleTagu(){
		List<PictureEntity> pictures = basePictureRepository.findByTagsTagText(TagToFind);
		for (PictureEntity picture:pictures) {
			List<String> tags= picture.getAllTagsText();
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
