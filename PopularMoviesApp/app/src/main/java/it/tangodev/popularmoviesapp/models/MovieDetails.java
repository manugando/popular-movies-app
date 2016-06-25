package it.tangodev.popularmoviesapp.models;


import java.util.List;

public class MovieDetails {
    private List<MovieReview> reviews;
    private List<MovieVideo> videos;

    public List<MovieReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<MovieReview> reviews) {
        this.reviews = reviews;
    }

    public List<MovieVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<MovieVideo> videos) {
        this.videos = videos;
    }
}
