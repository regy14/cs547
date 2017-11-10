package com.ozu.cs547;

import java.io.Serializable;
import java.util.Calendar;

public class Log implements Serializable {

  private Calendar date;
  private String request;
  private String destination;

  public Calendar getDate() {
    return date;
  }

  public void setDate(Calendar date) {
    this.date = date;
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Log{");
    sb.append("date=").append(date);
    sb.append(", request='").append(request).append('\'');
    sb.append(", destination='").append(destination).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
