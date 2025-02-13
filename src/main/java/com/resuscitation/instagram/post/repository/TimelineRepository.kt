package com.resuscitation.instagram.post.repository

import com.resuscitation.instagram.post.domain.Timeline
import org.springframework.data.jpa.repository.JpaRepository

interface TimelineRepository: JpaRepository<Timeline, Long> {
}