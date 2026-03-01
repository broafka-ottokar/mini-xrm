package com.example.minixrm.backend.web.facade;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.ActivityDtoFacade;
import com.example.minixrm.backend.core.facade.dto.ActivityDto;
import com.example.minixrm.backend.core.facade.dto.ActivitySortFieldDto;
import com.example.minixrm.backend.core.facade.dto.SortDirectionDto;
import com.example.minixrm.backend.web.openapi.v1.model.ActivityPageView;
import com.example.minixrm.backend.web.openapi.v1.model.ActivitySortFieldView;
import com.example.minixrm.backend.web.openapi.v1.model.ActivityView;
import com.example.minixrm.backend.web.openapi.v1.model.CreateOrUpdateActivityRequestView;
import com.example.minixrm.backend.web.openapi.v1.model.SortDirectionView;
import com.example.minixrm.backend.web.util.mapper.ActivitySortFieldViewMapper;
import com.example.minixrm.backend.web.util.mapper.ActivityViewMapper;
import com.example.minixrm.backend.web.util.mapper.SortDirectionViewMapper;

@Component
public class ActivityViewFacade {
	
	private final ActivityDtoFacade delegate;
	private final ActivityViewMapper activityViewMapper;
	private final ActivitySortFieldViewMapper activitySortFieldViewMapper;
	private final SortDirectionViewMapper sortDirectionViewMapper;
	
	public ActivityViewFacade(
			ActivityDtoFacade delegate,
			ActivityViewMapper activityViewmapper,
			ActivitySortFieldViewMapper activitySortFieldViewMapper,
			SortDirectionViewMapper sortDirectionViewMapper
	) {
		this.delegate = delegate;
		this.activityViewMapper = activityViewmapper;
		this.activitySortFieldViewMapper = activitySortFieldViewMapper;
		this.sortDirectionViewMapper = sortDirectionViewMapper;
	}

	public ActivityView getActivityById(Long activityId) {
		return activityViewMapper.toView(delegate.getActivityById(activityId));
	}
	
	public void deleteActivity(Long activityId) {
		delegate.deleteActivity(activityId);
	}

	public ActivityPageView findActivitiesByPartner(
			Long partnerId,
			Integer page,
			Integer pageSize,
			ActivitySortFieldView sortFieldView,
			SortDirectionView sortDirectionView
	) {
		SortDirectionDto sortDirectionDto = sortDirectionViewMapper.fromView(sortDirectionView);
		ActivitySortFieldDto sortFieldDto = activitySortFieldViewMapper.fromView(sortFieldView);
		return activityViewMapper.toView(delegate.findActivitiesByPartner(partnerId, page, pageSize, sortFieldDto, sortDirectionDto));
	}

	public ActivityView createOrUpdateActivity(
			Long activityId,
			CreateOrUpdateActivityRequestView createOrUpdateActivityRequestView
	) {
		ActivityDto dto = delegate.createOrUpdateActivity(activityId, activityViewMapper.fromView(createOrUpdateActivityRequestView));
		return activityViewMapper.toView(dto);
	}

}
