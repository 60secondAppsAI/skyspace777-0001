<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <rewardRedemption-table
            v-if="rewardRedemptions && rewardRedemptions.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:rewardRedemptions="rewardRedemptions"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-reward-redemptions="getAllRewardRedemptions"
             >

            </rewardRedemption-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import RewardRedemptionTable from "@/components/RewardRedemptionTable";
import RewardRedemptionService from "../services/RewardRedemptionService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    RewardRedemptionTable,
  },
  data() {
    return {
      rewardRedemptions: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllRewardRedemptions(sortBy='rewardRedemptionId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await RewardRedemptionService.getAllRewardRedemptions(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.rewardRedemptions.length) {
					this.rewardRedemptions = response.data.rewardRedemptions;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching rewardRedemptions:", error);
        }
        
      } catch (error) {
        console.error("Error fetching rewardRedemption details:", error);
      }
    },
  },
  mounted() {
    this.getAllRewardRedemptions();
  },
  created() {
    this.$root.$on('searchQueryForRewardRedemptionsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllRewardRedemptions();
    })
  }
};
</script>
<style></style>
