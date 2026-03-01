package com.example.minixrm.backend.core.domain;

import org.springframework.data.domain.Sort.Direction;

public enum SortDirection {
	
	ASC,
	DESC,
	;

	public Direction toDirection() {
		Direction direction;
		switch (this) {
			case ASC: {
				direction = Direction.ASC;
				break;
			}
			case DESC: {
				direction = Direction.DESC;
				break;
			}
			default: {
				throw new IllegalStateException("Unexpected value: " + this);
			}
		};
		return direction;
	}

}
