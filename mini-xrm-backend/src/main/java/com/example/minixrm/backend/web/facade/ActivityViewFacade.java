package com.example.minixrm.backend.web.facade;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.ActivityDtoFacade;
import com.example.minixrm.backend.web.openapi.v1.model.ActivityPageView;
import com.example.minixrm.backend.web.openapi.v1.model.ActivityView;
import com.example.minixrm.backend.web.openapi.v1.model.CreateOrUpdateActivityRequestView;
import com.example.minixrm.backend.web.util.mapper.ActivityViewMapper;

@Component
public class ActivityViewFacade {
	
	private ActivityDtoFacade delegate;
	private ActivityViewMapper mapper;
	
	public ActivityViewFacade(
			ActivityDtoFacade delegate,
			ActivityViewMapper mapper
	) {
		this.delegate = delegate;
		this.mapper = mapper;
	}

	public ActivityView getActivityById(Long activityId) {
		return mapper.toView(delegate.getActivityById(activityId));
	}
	
	public void deleteActivity(Long activityId) {
		delegate.deleteActivity(activityId);
	}

	public ActivityPageView findActivitiesByPartner(
			Long partnerId,
			Integer page,
			Integer pageSize
	) {
		return mapper.toView(delegate.findActivitiesByPartner(partnerId, page, pageSize));
	}

	public void createOrUpdateActivity(
			Long activityId,
			CreateOrUpdateActivityRequestView createOrUpdateActivityRequestView
	) {
		delegate.createOrUpdateActivity(activityId, mapper.fromView(createOrUpdateActivityRequestView));
	}

}
