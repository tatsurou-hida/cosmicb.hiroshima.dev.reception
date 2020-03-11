package jp.co.cosmicb.reception.repository;

import org.springframework.data.repository.Repository;

import jp.co.cosmicb.reception.entity.OfficeVisit;

public interface ReceptionRepository extends Repository<OfficeVisit, String> {

	public OfficeVisit findById(String _id);

	public OfficeVisit save(OfficeVisit entity);

}
