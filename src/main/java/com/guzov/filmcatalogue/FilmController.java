package com.guzov.filmcatalogue;

import com.guzov.filmcatalogue.dto.FilmRequest;
import com.guzov.filmcatalogue.model.FilmInfo;
import com.guzov.filmcatalogue.service.DynamoDBFilmService;
import com.guzov.filmcatalogue.service.DynamoDBFilmServiceException;
import com.guzov.filmcatalogue.util.Constants;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class FilmController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilmController.class);

    @Post
    public String query(@Body FilmRequest body) {
        LOGGER.info("Start");
        try {
            DynamoDBFilmService dynamoDBFilmService = new DynamoDBFilmService(Constants.FILM_TABLE_NAME);
            LOGGER.info("DynamoDB service initialized");
            List<FilmInfo> filmInfoList = dynamoDBFilmService.getByType(body.getType());
            LOGGER.info("Records queried {}", filmInfoList);
        } catch (DynamoDBFilmServiceException e) {
            return "Failed while loading records";
        }
        return "Success";
    }
}
