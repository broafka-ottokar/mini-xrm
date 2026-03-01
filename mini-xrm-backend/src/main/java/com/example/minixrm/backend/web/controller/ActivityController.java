package com.example.minixrm.backend.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.minixrm.backend.web.facade.ActivityViewFacade;
import com.example.minixrm.backend.web.openapi.v1.api.ActivityApi;
import com.example.minixrm.backend.web.openapi.v1.model.ActivityPageView;
import com.example.minixrm.backend.web.openapi.v1.model.ActivitySortFieldView;
import com.example.minixrm.backend.web.openapi.v1.model.ActivityView;
import com.example.minixrm.backend.web.openapi.v1.model.CreateOrUpdateActivityRequestView;
import com.example.minixrm.backend.web.openapi.v1.model.SortDirectionView;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Controller
@Validated
public class ActivityController implements ActivityApi {

	private final ActivityViewFacade facade;

	public ActivityController(
			ActivityViewFacade facade
	) {
		this.facade = facade;
	}

	@Override
	@RequestMapping(
			method = RequestMethod.DELETE,
			value = ActivityApi.PATH_DELETE_ACTIVITY,
			produces = { "application/json" }
	)
	public ResponseEntity<Void> deleteActivity(@NotNull @Min(1) @Max(9223372036854775807L) Long activityId) {
		facade.deleteActivity(activityId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@RequestMapping(
			method = RequestMethod.POST,
			value = ActivityApi.PATH_CREATE_ACTIVITY,
			produces = { "application/json" },
			consumes = { "application/json" }
	)
	public ResponseEntity<ActivityView> createActivity(
			@Valid CreateOrUpdateActivityRequestView createOrUpdateActivityRequestView
	) {
		return this.updateActivity(null, createOrUpdateActivityRequestView);
	}

	@Override
	@RequestMapping(
			method = RequestMethod.GET,
			value = ActivityApi.PATH_LIST_ACTIVITIES_BY_PARTNER_ID,
			produces = { "application/json" }
	)
	public ResponseEntity<ActivityPageView> listActivitiesByPartnerId(
	        @NotNull @Min(value = 1L) @Max(value = 9223372036854775807L)  @PathVariable("partnerId") Long partnerId,
	        @NotNull @Min(value = 0)  @Valid @RequestParam(value = "page", required = true) Integer page,
	        @NotNull @Min(value = 1)  @Valid @RequestParam(value = "pageSize", required = true) Integer pageSize,
	        @Valid @RequestParam(value = "sortField", required = false) @Nullable ActivitySortFieldView sortField,
	        @Valid @RequestParam(value = "sortDirection", required = false) @Nullable SortDirectionView sortDirection
	) {
		ActivityPageView view = facade.findActivitiesByPartner(partnerId, page, pageSize, sortField, sortDirection);
		return ResponseEntity.ok(view);
	}

	@Override
	@RequestMapping(
			method = RequestMethod.PUT,
			value = ActivityApi.PATH_UPDATE_ACTIVITY,
			produces = { "application/json" },
			consumes = { "application/json" }
	)
	public ResponseEntity<ActivityView> updateActivity(
			@NotNull @Min(1) @Max(9223372036854775807L) Long activityId,
			@Valid CreateOrUpdateActivityRequestView createOrUpdateActivityRequestView
	) {
		ActivityView view = facade.createOrUpdateActivity(activityId, createOrUpdateActivityRequestView);
		return ResponseEntity.ok(view);
	}

	@Override
	@RequestMapping(
	        method = RequestMethod.GET,
	        value = ActivityApi.PATH_LOAD_ACTIVITY,
	        produces = { "application/json" }
	)
	public ResponseEntity<ActivityView> loadActivity(@NotNull @Min(1) @Max(9223372036854775807L) Long activityId) {
		return ResponseEntity.ok(facade.getActivityById(activityId));
	}

}
