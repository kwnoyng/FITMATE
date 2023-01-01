<template>
  <transition>
    <div class="modal-mask">
      <div class="modal-wrapper" @click.stop="$emit('close')">
        <div class="modal-container" @click.stop="">
          <div class="modal-header">
            <slot name="header">default header</slot>
          </div>

          <div class="modal-body">
            <slot name="body">default body</slot>
          </div>

          <div class="modal-footer">
            <!-- <slot name="footer">default footer </slot> -->
            <button
              class="modal-default-button register-modal-return-btn"
              @click.stop="$emit('close')"
            >
              Return
            </button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: "MessageModal",
};
</script>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  width: 400px !important;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 9px !important;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
  /* border-collapse: separate; */
}

.modal-header h3 {
  margin-top: 0;
  color: rgb(87, 87, 87) !important;
  font-weight: 600;
  font-size: 24px;
}

.modal-body {
  /* margin: 20px auto; */
  color: rgb(87, 87, 87);
}

.modal-footer button {
  color: rgb(87, 87, 87);
  border: 1px solid rgb(87, 87, 87);
  min-width: 65px;
  height: 30px;
  font-weight: 600;
  padding: 0;
  line-height: 0;
}

.modal-footer button:hover {
  background-color: rgba(0, 0, 0, 0.068);
}

.modal-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 24px;
}

.modal-default-button {
  float: right;
  font-weight: 600;
  background-color: rgb(182, 179, 179);
  border: none !important;
  box-shadow: none;
  color: white !important;
}

/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}

.photo-container {
  margin: 0 auto;

  display: grid;
  grid-template-columns: repeat(5, 60px);
  justify-items: center;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

.photo-container img {
  border-radius: 9px;
  cursor: pointer;
}

.register-modal-return-btn {
  margin-top: 20px;
}
</style>
