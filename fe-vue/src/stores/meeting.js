import { ref } from "vue";
import { defineStore } from "pinia";

export const useMeetingStore = defineStore(
  "meeting",
  () => {
    const roomId = ref("");

    const setRoomId = (id) => {
      roomId.value = id;
    };
    return { roomId, setRoomId };
  },
  { persist: true }
);
