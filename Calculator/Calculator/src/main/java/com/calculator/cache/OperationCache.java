package com.calculator.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calculator.dao.OperationDAO;
import com.calculator.model.OperationDTO;
import com.calculator.model.OperationVO;
import com.calculator.model.UserSession;

@Component
public class OperationCache {
	private Map<Integer, Map<Integer, List<OperationDTO>>> operationUserCache = new ConcurrentHashMap<>();
	private Map<Integer, List<OperationDTO>> operationTypeCache = new ConcurrentHashMap<>();

	private final static Logger LOGGER = Logger.getLogger(OperationCache.class.toString());

	@Autowired
	private OperationDAO operationDAO;

	@Autowired
	private UserSession userSession;

	@PostConstruct
	private void loadData() {
		List<OperationDTO> operationList = operationDAO.getAll();
		Map<Integer, Map<Integer, List<OperationDTO>>> operationUserGroup = operationList.stream().collect(
																		   Collectors.groupingBy(OperationDTO::getUserId, Collectors
																				     .groupingBy(OperationDTO::getOperationId)));
		operationUserCache.putAll(operationUserGroup);
		Map<Integer, List<OperationDTO>> operationTypeGroup = operationList.stream()
																		.collect(Collectors
																		.groupingBy(OperationDTO::getOperationId));
		operationTypeCache.putAll(operationTypeGroup);

	}

	public List<OperationVO> getOperationByUser() {
		int userId = getUserId();
		Map<Integer, List<OperationDTO>> operationByUser = operationUserCache.get(userId);
		if (operationByUser == null) {
			return Collections.emptyList();
		}
		List<List<OperationDTO>> operations = operationByUser.entrySet().stream().map(Map.Entry::getValue)
															 .collect(Collectors.toList());
		List<OperationVO> result = new ArrayList<>();
		operations.forEach(o -> {
			o.forEach(o1 -> {
				OperationVO o2 = new OperationVO(o1);
				result.add(o2);
			});
		});
		return result;

	}

	public Map<Integer, List<OperationDTO>> getAllOperation() {
		return operationTypeCache;
	}

	private int getUserId() {
		return userSession.getUserId();
	}

	public void updateCache(OperationDTO operationDTO) {
		LOGGER.log(Level.SEVERE,
				"OperationCache:updateCache --> Updating cache with operatation info:" + operationDTO.toString());
		Map<Integer, List<OperationDTO>> operationsByUser = operationUserCache.get(getUserId());
		if (operationsByUser == null) {
			Map<Integer, List<OperationDTO>> operationTypeGroup = new HashMap<>();
			List<OperationDTO> operations = new ArrayList<>();
			operations.add(operationDTO);
			operationTypeGroup.put(operationDTO.getOperationId(), operations);
			operationUserCache.put(getUserId(), operationTypeGroup);
		} else {
			List<OperationDTO> operationByType = operationsByUser.get(operationDTO.getOperationId());
			if (operationByType == null) {
				List<OperationDTO> operationsAddNew = new ArrayList<>();
				operationsAddNew.add(operationDTO);
				operationsByUser.put(operationDTO.getOperationId(), operationsAddNew);
			} else {
				operationByType.add(operationDTO);
			}

		}

		List<OperationDTO> operationList = operationTypeCache.get(operationDTO.getOperationId());
		if (operationList != null) {
			operationList.add(operationDTO);
		} else {
			List<OperationDTO> operationsNew = new ArrayList<>();
			operationsNew.add(operationDTO);
			operationTypeCache.put(operationDTO.getOperationId(), operationsNew);
		}

	}

	@PreDestroy
	private void clearCache() {
		operationUserCache = null;
	}
}
