package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class VisitorListRepositoryImpl implements VisitorListRepository {

	@Override
	public String getCustomer() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public <S extends OfficeVisit> List<S> saveAll(Iterable<S> entities) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<OfficeVisit> findAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<OfficeVisit> findAll(Sort sort) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public <S extends OfficeVisit> S insert(S entity) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public <S extends OfficeVisit> List<S> insert(Iterable<S> entities) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public <S extends OfficeVisit> List<S> findAll(Example<S> example) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public <S extends OfficeVisit> List<S> findAll(Example<S> example, Sort sort) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Page<OfficeVisit> findAll(Pageable pageable) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public <S extends OfficeVisit> S save(S entity) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Optional<OfficeVisit> findById(String id) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public Iterable<OfficeVisit> findAllById(Iterable<String> ids) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public long count() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void deleteById(String id) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void delete(OfficeVisit entity) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void deleteAll(Iterable<? extends OfficeVisit> entities) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void deleteAll() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public <S extends OfficeVisit> Optional<S> findOne(Example<S> example) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public <S extends OfficeVisit> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public <S extends OfficeVisit> long count(Example<S> example) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public <S extends OfficeVisit> boolean exists(Example<S> example) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public List<OfficeVisit> findByVisitedAtDesc() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<OfficeVisit> findByVisiterAtAndLeftatDesc() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int updateVisitorLeft(String _id, String person_to_visit) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
