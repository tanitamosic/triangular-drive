package com.NWT_KTS_project.repository;

import com.NWT_KTS_project.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    public List<Issue> findByUser_Id(int id);

    public List<Issue> findByAdmin_Id(int id);



}
