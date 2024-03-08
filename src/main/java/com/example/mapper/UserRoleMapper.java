package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.vo.UserRole;

@Mapper
// @Mapper스캔기능
public interface UserRoleMapper {

	// 해당 유저의롤을 추가하는 기능
	void insertUserRole(UserRole userRole);
	// 해당 유저의롤을 삭제하는 기능
	void deleteUserRole(UserRole userRole);
	List<UserRole> getUserRolesByUserNo(int userNo);
}
