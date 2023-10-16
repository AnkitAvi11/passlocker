package com.passlocker.passlocker.controllers.responses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.passlocker.passlocker.entities.UserEntity;
import com.passlocker.passlocker.exceptions.GenericExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

@JsonPropertyOrder({"totalItems", "nextLink", "prevLink", "current", "timestamp", "items"})
public class GenericPaginatedResponse {
    private List<UserEntity> items;
    private long totalItems;
    private int offset;
    private int limit;
    private String nextLink;
    private String prevLink;
    private String current;
    private String timeStamp;

    public GenericPaginatedResponse() {
        this.timeStamp = new Date(System.currentTimeMillis()).toString();
    }

    public GenericPaginatedResponse(List<UserEntity> items, long totalItems, String nextLink, String prevLink, String current, String timeStamp) {
        this.items = items;
        this.totalItems = totalItems;
        this.nextLink = nextLink;
        this.prevLink = prevLink;
        this.current = current;
        this.timeStamp = timeStamp;
    }

    public List<UserEntity> getItems() {
        return items;
    }

    public GenericPaginatedResponse setItems(List<UserEntity> items) {
        this.items = items;
        return this;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public GenericPaginatedResponse setTotalItems(long totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    public String getNextLink() {
        return nextLink;
    }


    public String getPrevLink() {
        return prevLink;
    }


    public String getCurrent() {
        return current;
    }

    public GenericPaginatedResponse setCurrent(String current) {
        this.current = current;
        return this;
    }

    public GenericPaginatedResponse setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public GenericPaginatedResponse setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    private String generatePrevPageLink(int offset, int limit) {
        if (offset <= 0) {
            return "";
        }

        int prevOffset = Math.max(0, offset - limit);
        return "?limit="+limit+"&offset="+prevOffset;
    }

    private String generateNextPageLink(int offset, int limit) {
        if (offset < 0) {
            return "";
        }

        int nextOffset = offset + limit;
        return "?limit="+limit+"&offset="+nextOffset;
    }

    public GenericPaginatedResponse build(HttpServletRequest request) {
        this.nextLink = request.getRequestURI()+generateNextPageLink(this.offset, this.limit);
        this.prevLink = request.getRequestURI()+generatePrevPageLink(this.offset, this.limit);
        this.current = request.getRequestURI();
        return this;
    }
}
