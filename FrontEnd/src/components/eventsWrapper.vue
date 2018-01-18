<template>
  <div>
  <eventsGraph id="canvas" style="width=90%"></eventsGraph>
</div>
</template>

<script>
import eventsGraph from './eventsGraph'
export default {
  name: 'eventsWrapper',
  components: { eventsGraph }
}
</script>
