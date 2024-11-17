import http from "../http-common"; 

class RewardRedemptionService {
  getAllRewardRedemptions(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/rewardRedemption/rewardRedemptions`, searchDTO);
  }

  get(rewardRedemptionId) {
    return this.getRequest(`/rewardRedemption/${rewardRedemptionId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/rewardRedemption?field=${matchData}`, null);
  }

  addRewardRedemption(data) {
    return http.post("/rewardRedemption/addRewardRedemption", data);
  }

  update(data) {
  	return http.post("/rewardRedemption/updateRewardRedemption", data);
  }
  
  uploadImage(data,rewardRedemptionId) {
  	return http.postForm("/rewardRedemption/uploadImage/"+rewardRedemptionId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new RewardRedemptionService();
