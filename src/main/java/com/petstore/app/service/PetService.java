package com.petstore.app.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.petstore.app.dto.PetRequest;
import com.petstore.app.dto.PetResponse;
import com.petstore.app.entity.Category;
import com.petstore.app.entity.Pet;
import com.petstore.app.entity.Photo;
import com.petstore.app.entity.Tag;
import com.petstore.app.exception.PetAlreadyExistsException;
import com.petstore.app.exception.PetNotFoundException;
import com.petstore.app.mapper.CategoryMapper;
import com.petstore.app.mapper.PetMapper;
import com.petstore.app.mapper.PhotoMapper;
import com.petstore.app.mapper.TagMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {
	
	private final PetMapper petMapper;
	private final CategoryMapper categoryMapper;
	private final PhotoMapper photoMapper;
	private final TagMapper tagMapper;
	
	//全検索
	public List<PetResponse> findAll(){
		List<Pet> pets = petMapper.findAll(); //id, category_id, name, status
		List<PetResponse> petResponses = new ArrayList<>(); //id, category, name, photoUrls, tags, status
		//petResponse - pet -> category, photoUrls, tags
		pets.forEach(pet -> {
			PetResponse petResponse = createResponse(pet); //取得したpetListでレスポンスを作成
			petResponses.add(petResponse); //ResponseList に追加
		});
		return petResponses;
	}
	
	//id検索
	public PetResponse findById(Long id) {
		Pet pet = petMapper.findById(id).orElseThrow(() -> new PetNotFoundException(id)); //idが存在しないときエラー出力		
		PetResponse petResponse = createResponse(pet);
		return petResponse;
	}
	
	//status検索
	public List<PetResponse> findByStatus(List<String> statuses){ //pet/findByStatus?statuses=*,*,*
		List<PetResponse> petResponses = new ArrayList<>();
		statuses.forEach(status -> {
			List<Pet> pets = petMapper.findByStatus(status); //ステータス検索
			pets.forEach(pet -> {
				PetResponse petResponse = createResponse(pet);
				petResponses.add(petResponse);
			});
		});
		return petResponses;
	}
	
	//tag検索
	public List<PetResponse> findByTags(List<String> tags) {
		List<PetResponse> petResponses = new ArrayList<>();
		tags.forEach(tag -> {
			List<Pet> pets = petMapper.findByTag(tag); //タグ検索
			pets.forEach(pet -> {
				PetResponse petResponse = createResponse(pet);
				petResponses.add(petResponse);
			});
		});
		return petResponses;
	}
	
	//レスポンス形式作成用
	public PetResponse createResponse(Pet pet) {
		//pet(id, categoryId, name, status)

		//petのcategoryIdが0の時、categoryは空配列で表示する(category設定なし)
		//petのcategoryIdが0以外の時、categoryIdを使用してCategoryを検索する
		Category category = new Category();
		if(pet.getCategoryId() != 0L) {
			category = categoryMapper.findById(pet.getCategoryId());
		}
		
		//petIdを使用して写真を検索、リスト化
		List<Photo> photos = photoMapper.findByPetId(pet.getId()); 
		List<String> photoUrls = new ArrayList<>();
		photos.forEach(photo -> photoUrls.add(photo.getUrl())); //photoUrlsSet
		
		//petIdを使用してtagを検索、リスト化
		List<Tag> tags = tagMapper.findByPetId(pet.getId());  // tagSet
		
		PetResponse petResponse = new PetResponse(pet, category, photoUrls, tags);
		return petResponse;
	}
	
	
	//登録処理
	@Transactional
	public PetResponse addPet(PetRequest petRequest) { //category, name, photoUrls, tags, status
		//Petの名前は重複しないようにする。
		Pet findPet = petMapper.findByName(petRequest.getName());
		if(findPet != null) {
			throw new PetAlreadyExistsException("登録しようとしているペットの名前は既に存在しています。");
		}
		
//		//insert Category	
		Category category = editCategory(petRequest);
		
		//insert Pet
		Pet pet = new Pet(); //categoryId, name, status
		Long categoryId;
		if(category.getId() == null) {
			categoryId = 0L;
		}else {
			categoryId = category.getId();
		}
		pet.setCategoryId(categoryId);
		pet.setName(petRequest.getName());		
		pet.setStatus(petRequest.getStatus());
		petMapper.insert(pet); 
		
//		//insert photoUrls
		List<String> photoUrls = petRequest.getPhotoUrls();
		String newPetName = pet.getName();
		Pet newPet = petMapper.findByName(newPetName);
		photoUrls.forEach(url ->{
			Long petId = newPet.getId();
			photoMapper.insert(petId, url);
		});
		
		//insert Tags
		List<Tag> requestTags = petRequest.getTags(); //id,name
		List<Tag> tags = new ArrayList<>();
		
		//リクエストの中にタグが入っていないときは空の配列を渡す。
		//入っているときはタグを検索して、あったらそれを使う、なかったら新規追加
		if(!(requestTags == null || requestTags.isEmpty())) {
			requestTags.forEach(tag -> {
				String tagName = tag.getName();
				Long petId = newPet.getId();
				if(tagMapper.findByName(tagName) == null) {
					tagMapper.insertTag(tagName);
					Long newTagId = tagMapper.findByName(tagName).getId();
					tagMapper.insertPetTag(petId, newTagId);
					tags.add(tag);
				}else {
					Long tagId = tagMapper.findByName(tagName).getId();
					tagMapper.insertPetTag(petId, tagId);
					tags.add(tag);
				}
			});
		}
		
		return new PetResponse(pet, category, photoUrls, tags);
	}
	
	public Category editCategory(PetRequest petRequest) {
		Category category = new Category();
		String requestCategoryName = petRequest.getCategory().getName();
		if(requestCategoryName != null) { //リクエストに名前が入っていたら検索、入っていない時は空の配列を渡す。
			category = categoryMapper.findByName(requestCategoryName); //カテゴリーがすでに存在しているか検索
			if(category == null) { //検索結果で存在しない場合、新規追加
				categoryMapper.insert(requestCategoryName);
				category = categoryMapper.findByName(requestCategoryName); //追加したカテゴリーで検索して更新
			}
		}
		return category;
	}
	
	//更新処理
	public PetResponse updatePet(Long id, PetRequest petRequest) { //id, category, name, photoUrls, tags, status
		Pet pet = petMapper.findById(id).orElseThrow(() -> new PetNotFoundException(id)); //id検索でなかったらエラー
		
		//UpdateCategory
		Category category = editCategory(petRequest);
		
		//UpdatePet
		//リクエストのcategoryIdがnullの時、categoryIdは0とする。
		Long categoryId;
		if(category.getId() == null) {
			categoryId = 0L;
		}else {
			categoryId = category.getId();
		}
		pet.setCategoryId(categoryId);
		pet.setName(petRequest.getName());
		pet.setStatus(petRequest.getStatus());
		petMapper.update(pet); 
		
		//UpdatePhotoUrls
		List<String> RequestPhotoUrls = petRequest.getPhotoUrls(); //リクエスト
		List<Photo> oldPhotos = photoMapper.findByPetId(pet.getId()); //DB上データ
		List<String> oldPhotoUrls = new ArrayList<>();
		oldPhotos.forEach(photo -> oldPhotoUrls.add(photo.getUrl()));
		//DBのデータと比較して一致していなかったらいったん削除して再作成
		if(!oldPhotoUrls.equals(RequestPhotoUrls)) { 
			photoMapper.deleteByPetId(pet.getId());
			RequestPhotoUrls.forEach(url -> photoMapper.insert(pet.getId(), url));
		}
		
		//UpdateTags
		List<Tag> requestTags = petRequest.getTags(); //リクエスト
		List<Tag> oldTags = tagMapper.findByPetId(pet.getId()); //DB上データ
		//DBのデータと比較して一致していなかったら再作成
		if (!oldTags.equals(requestTags)) {
		    tagMapper.deleteByPetId(pet.getId());
		    requestTags.forEach(tag -> {
		        Tag existingTag = tagMapper.findByName(tag.getName());
		        if (existingTag == null) {
		            // タグが存在しない場合の処理
		            tagMapper.insertTag(tag.getName());
		            Long newTagId = tagMapper.findByName(tag.getName()).getId(); // 新しいタグのID取得
		            tagMapper.insertPetTag(pet.getId(), newTagId);
		        } else {
		            // タグが既に存在する場合の処理
		            tagMapper.insertPetTag(pet.getId(), existingTag.getId());
		        }
		    });
		}
		PetResponse petResponse = createResponse(pet);
		return petResponse;
	}
	
	public void deletePet(Long id) {
		petMapper.findById(id).orElseThrow(() -> new PetNotFoundException(id));
		petMapper.delete(id);
		tagMapper.deleteByPetId(id);
	}
	

	
}
