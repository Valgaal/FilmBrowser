package com.example.nikita.filmbrowser;

import com.example.nikita.filmbrowser.Model.Network.Models.SearchResultModel;
import com.example.nikita.filmbrowser.Model.DB.Converters;
import com.example.nikita.filmbrowser.Model.DB.Movie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ConvertersTest {

    SearchResultModel resultModel;

    @Before
    public void setUp(){
        resultModel = new SearchResultModel();
        resultModel.setName("Avengers");
        resultModel.setVoteAverage(5.434);
        resultModel.setOriginalTitle("Avg");
        resultModel.setOverview("Blockbaster");
    }

    @Test
    public void convertToMovieTest(){
        Movie movie = Converters.convertToMovie(resultModel);
        Assert.assertNull(movie.getOverview());
        Assert.assertEquals(movie.getRatingAvg(), 5.434, 1);
        Assert.assertEquals(movie.getTitle(), "Avengers");
    }
}
