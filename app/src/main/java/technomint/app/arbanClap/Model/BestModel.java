package technomint.app.arbanClap.Model;

public class BestModel {

    private String name ;
    private String image_url;


    public BestModel(String category_image) {
    }

    public BestModel(String name, String description, String rating, int nb_episode, String image_url, String categorie, String studio) {
        this.name = name;
        this.image_url = image_url;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }







    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


}
