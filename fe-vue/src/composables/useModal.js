import { ref } from "vue";

export function useModal() {
  const isModalOpen = ref(false);
  const selectedMember = ref(null);

  const openModal = (member = null) => {
    if (member) {
      selectedMember.value = member;
    }
    isModalOpen.value = true;
  };

  const closeModal = () => {
    isModalOpen.value = false;
  };

  return {
    isModalOpen,
    selectedMember,
    openModal,
    closeModal,
  };
}
