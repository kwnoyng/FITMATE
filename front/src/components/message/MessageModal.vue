<template>
  <transition>
    <div class="modal-mask">
      <div class="modal-wrapper" @click.stop="$emit('close')">
        <div class="modal-container modal-container-message" @click.stop="">
          <div class="modal-header">
            <slot name="header">default header</slot>
          </div>

          <div class="modal-body">
            <slot name="body">default body</slot>
          </div>

          <div class="modal-footer modal-footer-message">
            <slot name="footer">default footer </slot>
            <button
              class="modal-default-button modal-default-button-message"
              @click.stop="$emit('close')"
            >
              X
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
  cursor: default;
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
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}

.modal-container-message {
  height: 400px;
  display: flex;
  flex-direction: column;
}

.modal-header h3 {
  margin-top: 0;
  color: rgb(49, 49, 49);
}

.modal-body {
  font-weight: 600;
  color: rgb(92, 90, 90);
  text-align: left;
  font-size: 16px;
}

.message-content-body {
  margin: 15px auto 0 23px;
}

.modal-footer .modal-default-button {
  float: right;
}

.modal-default-button-message {
  min-width: 40px !important;
  height: 40px !important;
  font-weight: 600;
  border: none !important;
  background-color: rgb(182, 179, 179);
  color: white !important;
  box-shadow: none;
  margin-top: 20px;
}

.modal-footer-message {
  margin-top: auto;
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

.go-to-profile {
  text-decoration: none !important;
  /* border: 1px solid rgb(87, 87, 87); */
  border-radius: 9px;
  min-width: 90px;
  height: 40px;
  display: inline-block;
  color: white;

  font-size: 15px;
  transition: ease 0.5s;
  cursor: pointer;
  line-height: 2.4;
  font-weight: 600;
  background-color: rgb(182, 179, 179);
  border-bottom: none;
}

.go-to-profile:hover {
  /* color: rgb(87, 87, 87); */
  border-bottom-color: rgb(87, 87, 87);
  background-color: rgba(0, 0, 0, 0.068);
}

.send-message {
  text-decoration: none !important;
  /* border: 1px solid rgb(87, 87, 87); */
  border-radius: 9px;
  min-width: 125px;
  height: 40px;
  display: inline-block;
  color: white;

  font-size: 15px;
  transition: ease 0.5s;
  line-height: 2.4;
  font-weight: 600;
  background-color: rgb(182, 179, 179);
  border-bottom: none;
}
.send-message:hover {
  /* color: rgb(87, 87, 87); */
  border-bottom-color: rgb(87, 87, 87);
  background-color: rgba(0, 0, 0, 0.068);
}

.profileAndMessage {
  display: flex;
  gap: 12px;
}

.profileAndMessage button {
  cursor: pointer;
}

.profileAndMessage a {
  line-height: 2.5;
}
</style>
