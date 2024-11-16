package com.kientran.shipping_service.imgur;

public class ImgurResponse {
    private ImgurData data;

    // Getter và Setter
    public ImgurData getData() {
        return data;
    }

    public void setData(ImgurData data) {
        this.data = data;
    }

    public static class ImgurData {
        private String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
