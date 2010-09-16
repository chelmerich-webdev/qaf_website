<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="qaf" uri="qafTags" %>
<%@taglib prefix="ch67" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <qaf:head-include />
    </head>
    <body>
        <qaf:body-top-include />
        <h2>New Film Entry</h2>
        <form action="/SaveFilmEntry.do" method="POST">
            <fieldset><legend>Film</legend>
                <dl>
                    <ch67:inputitem name="title" label="Title" />
                    <ch67:inputitem name="year" label="Year" />
                    <input type="text" name="directorFirst"
                    <ch67:inputitem name="directorFirst" label="Director's First Name" />
                    <ch67:inputitem name="directorLast" label="Director's Last Name" />
                    <ch67:inputitem name="synopsis" label="Synopsis" type="textarea" />
                </dl>
            </fieldset>
            <fieldset><legend>QAF Series Screening</legend>
                <dl>
                    <ch67:inputitem name="series" label="Series" />
                    <ch67:inputitem name="screeningYear" label="Year" />
                    <ch67:inputitem name="screeningMonth" label="Month"/>
                    <ch67:inputitem name="screeningDate" label="Date" />
                    <ch67:inputitem name="screeningHour" label="Hour" />
                    <ch67:inputitem name="screeningMinute" label="Minute" />
                    <ch67:inputitem name="screeningVenue" label="Venue" />
                    <ch67:inputitem name="venueAddress1" label="Address 1" />
                    <ch67:inputitem name="venueAddress2" label="Address 2" />
                    <ch67:inputitem name="venuePhoneNumber" label="Phone Number" />
                    <ch67:inputitem name="presenterFirst" label="Presenter's First Name" />
                    <ch67:inputitem name="presenterLast" label="Presenter's Last Name" />

                </dl>
            </fieldset>
            <input type="submit" value="Submit"/>
        </form>
        <qaf:body-bottom-include />
    </body>

</html>
