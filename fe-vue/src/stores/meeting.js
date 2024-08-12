import { ref } from "vue";
import { defineStore } from "pinia";

export const useMeetingStore = defineStore(
  "meeting",
  () => {
    const roomId = ref("");
    const hints = ref([]);
    const storedHintNo = ref([]);

    const setRoomId = (id) => {
      roomId.value = id;
    };

    const setHint = (hint, currentNo) => {
      hints.value.push({
        no: currentNo,
        content: hint,
      });
      storedHintNo.value.push(currentNo);
      hints.value.sort((a, b) => a.no - b.no);
    };

    const clearHint = () => {
      hints.value = [];
      storedHintNo.value = [];
    };
    return { roomId, setRoomId, hints, storedHintNo, setHint, clearHint };
  },
  { persist: true }
);
