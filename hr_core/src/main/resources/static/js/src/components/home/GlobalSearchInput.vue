<template>
  <form action="#" class="hidden lg:block lg:pl-3.5" method="GET">
    <label class="sr-only" for="topbar-search">Search</label>
    <div class="relative lg:w-96 mt-3">
      <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
        <svg class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20"
             xmlns="http://www.w3.org/2000/svg">
          <path clip-rule="evenodd"
                d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                fill-rule="evenodd">
          </path>
        </svg>
      </div>
      <input id="topbar-search"
             class="py-1 bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full pl-10 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
             name="search"
             placeholder="Search"
             type="text">
    </div>
  </form>
</template>

<script>
export default {
  data(){
    return{

    }
  }
}
</script>